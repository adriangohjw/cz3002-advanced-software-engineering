class ProductsController < ApplicationController
  def index
    products = Product.all.to_a

    if params[:category]
      product_category_ids = convert_category_params_to_ids(names: params[:category])
      products.delete_if { |product| !product_category_ids.include?(product.product_category_id) }
    end

    product_ids_to_delete_array = Array.new
    products.each do |product|
      if params[:min_price] || params[:max_price]
        if !product.discounts.blank? && product.latest_discount.instance_of?(DiscountSingle)
          product_ids_to_delete_array << product.id if params[:min_price] && (product.latest_discount.price < params[:min_price].to_f)
          product_ids_to_delete_array << product.id if params[:max_price] && (product.latest_discount.price > params[:max_price].to_f)
        else
          product_ids_to_delete_array << product.id if params[:min_price] && (product.price < params[:min_price].to_f)
          product_ids_to_delete_array << product.id if params[:max_price] && (product.price > params[:max_price].to_f)
        end
      end
    end
    products.delete_if { |product| product_ids_to_delete_array.include?(product.id) }

    paginatable_products = Kaminari.paginate_array(products)
                                   .page(params[:page])
                                   .per(10)

    response = Hash.new
    
    response[:total_results_count] = paginatable_products.count

    response[:products] = Array.new
    paginatable_products.each do |product|
      product_hash = Hash.new
      product_hash[:id] = product.id
      product_hash[:name] = product.name
      product_hash[:price] = product.price
      product_hash[:discounted_price] = product.latest_discount.price if product.latest_discount.instance_of?(DiscountSingle)
      response[:products] << product_hash
    end  
    
    json_response(response)
  end

  def show
    begin
      product = Product.find(params[:id])
      latest_discount = product.latest_discount

      response = Hash.new

      response[:id] = product.id
      response[:name] = product.name
      response[:category] = product.product_category.name
      response[:undiscounted_price] = product.price

      response[:discount] = Hash.new
      unless latest_discount.nil?
        response[:discount][:type] = latest_discount.type
        case latest_discount.type
        when DiscountSingle.to_s
          response[:discount][:price] = latest_discount.price
        when DiscountBulk.to_s
          response[:discount][:bulk_quantity] = latest_discount.bulk_quantity
          response[:discount][:bulk_price] = latest_discount.bulk_price
        end
      end

      total_quantity_purchased_last_30_days = \
        OrderProduct.where(product: product)
                    .last_30_days
                    .map { |x| x.quantity }
                    .inject(0) { |sum, x| sum + x }
      response[:total_quantity_purchased_last_30_days] = total_quantity_purchased_last_30_days

      json_response(response)
    rescue => exception
      json_response(exception, :bad_request)
    end
  end

  private

  def convert_category_params_to_ids(names:)
    product_category_names = Array.new
    params[:category].each do |category|
      category_name = category[1]
      product_category_names << ProductCategory::OPTIONS[category_name.to_sym][:name]
    end

    product_category_ids = ProductCategory.where(name: product_category_names).pluck(:id)
  end

end