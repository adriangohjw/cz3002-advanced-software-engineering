class OrdersController < ApplicationController

  def show
    order = Order.find(params[:order_id])

    response = Hash.new

    response[:id] = order.id
    response[:store_name] = order.store.name
    response[:created_at] = helpers.sanitize_timezone_object(order.created_at)
    response[:products] = Array.new
    response[:total_discounted_amount] = 0

    order.order_products.each do |order_product|
      order_product_hash = Hash.new
      
      order_product_hash[:id] = order_product.product.id
      order_product_hash[:name] = order_product.product.name
      order_product_hash[:total_undiscounted_product_price] = order_product.total_undiscounted_product_price
      order_product_hash[:quantity] = order_product.quantity
      order_product_hash[:total_discount_amount] = order_product.total_discount_amount

      total_discounted_product_price = order_product.total_undiscounted_product_price - order_product.total_discount_amount
      order_product_hash[:total_discounted_price] = total_discounted_product_price

      response[:total_discounted_amount] += total_discounted_product_price

      response[:products] << order_product_hash
    end

    json_response(response)
  end

  def show_all_for_shopper
    shopper = Shopper.find(params[:user_id])

    response = Hash.new
    response[:orders] = Array.new

    shopper.orders.each do |order|
      order_hash = Hash.new
      order_hash[:id] = order.id
      order_hash[:details] = Hash.new

      order_hash[:details][:store_name] = order.store.name

      order_hash[:details][:items_purchased_count] = 0
      order.order_products.each do |order_product|
        order_hash[:details][:items_purchased_count] += order_product.quantity
      end

      order_hash[:details][:created_at] = helpers.sanitize_timezone_object(order.created_at)

      response[:orders] << order_hash
    end

    json_response(response)
  end

  def post
    shopper = Shopper.find(params[:user_id])
    store = Store.find(params[:store_id])

    begin
      ActiveRecord::Base.transaction do
        @order = Order.create!(user: shopper,
                               store: store,
                               stripe_charge_id: params[:charge_id])

        cart_products = shopper.cart_products

        cart_products.each do |cart_product|
          total_undiscounted_product_price = cart_product.quantity * cart_product.product.price
          total_discounted_price = Discount.compute_total_discounted_price(product: cart_product.product, 
                                                                           quantity: cart_product.quantity)
          OrderProduct.create!(order: @order,
                               product: cart_product.product,
                               total_undiscounted_product_price: total_undiscounted_product_price,
                               quantity: cart_product.quantity,
                               total_discount_amount: total_undiscounted_product_price - total_discounted_price)
        end

        cart_products.destroy_all
      end
      
      json_response(@order, :created)

    rescue => exception
      json_response(exception.message, :bad_request)
    end
  end
end