class ProductsController < ApplicationController
  def index
    products = Product.all.to_a

    products.each do |product|
      if params[:category]
        product_category_ids = convert_category_params_to_ids(names: params[:category])
        products.delete_if { |product| !product_category_ids.include?(product.product_category_id) }
      end

      if params[:min_price] || params[:max_price]
        if !product.discounts.blank? && product.latest_discount.instance_of?(DiscountSingle)
          products.delete_if { |product| product.price - product.latest_discount.price < params[:min_price].to_f } if params[:min_price] 
          products.delete_if { |product| product.price - product.latest_discount.price > params[:max_price].to_f } if params[:max_price] 
        else
          products.delete_if { |product| product.price < params[:min_price].to_f } if params[:min_price] 
          products.delete_if { |product| product.price > params[:max_price].to_f } if params[:max_price] 
        end
      end
    end

    paginatable_products = Kaminari.paginate_array(products)
                                   .page(params[:page])
                                   .per(10)

    response = Hash.new
    response[:total_results_count] = paginatable_products.count
    response[:products] = paginatable_products
    
    json_response(response)
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