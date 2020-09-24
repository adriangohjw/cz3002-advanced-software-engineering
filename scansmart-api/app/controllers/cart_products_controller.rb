class CartProductsController < ApplicationController
  def show_all_for_shopper
    shopper = Shopper.find(params[:id])
    cart_products = shopper.cart_products.includes(product: [:discounts])

    response = Hash.new
    response[:cart_products] = Array.new
    response[:total_discounted_cart_amount] = 0

    cart_products.each do |cart_product|
      product_hash = Hash.new

      product_hash[:id] = cart_product.id
      product_hash[:quantity] = cart_product.quantity

      most_recent_discount = cart_product.product.latest_discount
      if !most_recent_discount.blank?
        product_hash[:discount] = Hash.new
        product_hash[:discount][:id] = most_recent_discount.id 
        product_hash[:discount][:type] = most_recent_discount.type 
        product_hash[:discount][:price] = most_recent_discount.price 
        product_hash[:discount][:bulk_quantity] = most_recent_discount.bulk_quantity 
        product_hash[:discount][:bulk_price] = most_recent_discount.bulk_price 
      end

      product_hash[:product] = Hash.new
      product_hash[:product][:id] = cart_product.product.id
      product_hash[:product][:name] = cart_product.product.name
      product_hash[:product][:price] = cart_product.product.price


      total_discounted_price = \
        Discount.compute_total_discounted_price(product: cart_product.product,
                                                quantity: cart_product.quantity)

      product_hash[:total_discounted_price] = total_discounted_price
      response[:total_discounted_cart_amount] += total_discounted_price
        
      response[:cart_products] << product_hash
    end

    json_response(response)
  end

  def clear_all
    shopper = Shopper.find(params[:id])
    shopper.cart_products.destroy_all

    json_response("Successfully cleared shopper's cart")
  end

  def increase_quantity
    cart_product = CartProduct.find(params[:id])
    cart_product.quantity += 1
    cart_product.save

    json_response("Successfully updated shopper's cart")
  end

  def decrease_quantity
    cart_product = CartProduct.find(params[:id])
    cart_product.quantity -= 1 if cart_product.quantity != 0
    cart_product.save

    json_response("Successfully updated shopper's cart")
  end
end