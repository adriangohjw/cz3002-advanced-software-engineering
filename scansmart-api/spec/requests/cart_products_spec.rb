require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Cart Products API', type: :request do
  before do
    @shopper = FactoryBot.create(:shopper)

    @product_category = FactoryBot.create(:product_category)

    @product_1 = FactoryBot.create(:product, product_category: @product_category)
    @cart_product_1 = FactoryBot.create(:cart_product, user: @shopper,
                                                       product: @product_1)

    @product_2 = FactoryBot.create(:product, product_category: @product_category)
    @cart_product_2 = FactoryBot.create(:cart_product, user: @shopper,
                                                       product: @product_2)
    @discount_single = FactoryBot.create(:discount_single, product: @product_2,
                                                           price: @product_2.price * rand(50..100)/100.0)

    @product_3 = FactoryBot.create(:product, product_category: @product_category)
    @cart_product_3 = FactoryBot.create(:cart_product, user: @shopper,
                                                       product: @product_3)    
    @discount_bulk = FactoryBot.create(:discount_bulk, product: @product_3)
    @discount_bulk.update(bulk_price: @discount_bulk.bulk_quantity * @product_3.price * rand(50..100)/100.0)
  end

  describe "GET /users/:id/cart" do
    context "parameters are valid" do
      let (:shopper_id) { @shopper.id }
      before { get "/users/#{shopper_id}/cart" }

      it "return the cart details" do
        json_response = JSON.parse(response.body)
        total_discounted_cart_amount = 0
        expect(json_response["cart_products"].count).to eq(@shopper.cart_products.count)

        json_response["cart_products"].each do |cart_product|
          cart_product_from_db = CartProduct.find(cart_product["id"])
          

          expect(cart_product["quantity"]).to eq(cart_product_from_db.quantity)

          expect(cart_product["product"]["id"]).to eq(cart_product_from_db.product.id)
          expect(cart_product["product"]["name"]).to eq(cart_product_from_db.product.name)
          expect(cart_product["product"]["price"]).to eq(cart_product_from_db.product.price)

          if cart_product["discount"].nil?
            expect(cart_product_from_db.product.discounts.blank?).to be true
          else
            expect(cart_product["discount"]["id"]).to eq(cart_product_from_db.product.latest_discount.id)
          end

          cart_product_from_db_total_discounted_price = \
            Discount.compute_total_discounted_price(product: cart_product_from_db.product, 
                                                    quantity: cart_product_from_db.quantity)
          expect(cart_product["total_discounted_price"]).to eq(cart_product_from_db_total_discounted_price)
                                                    
          total_discounted_cart_amount += cart_product_from_db_total_discounted_price
        end

        expect(json_response["total_discounted_cart_amount"]).to eq(total_discounted_cart_amount)
      end

      it "returns status code 200" do
        expect(response).to have_http_status(200)
      end
    end
    
    context "user does not exist" do
      let (:shopper_id) { nil }
      before { get "/users/#{shopper_id}/cart" }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end
  end

  describe "DELETE /users/:id/cart" do
    let (:shopper_id) { @shopper.id }
      
    it "deletes all cart_products record for user" do
      expect(@shopper.cart_products.count).to eq(3)

      delete "/users/#{shopper_id}/cart"

      expect(@shopper.cart_products.count).to eq(0)
    end

    it 'returns status code 200' do
      delete "/users/#{shopper_id}/cart"

      expect(response).to have_http_status(200)
    end
  end

  describe "PUT /cart_products/:id/increase" do
    let (:id) { @cart_product_1.id }

    it "increases the quantity of cart_product by 1" do
      before_quantity = @cart_product_1.quantity

      put "/cart_products/#{id}/increase"

      expect(CartProduct.find(@cart_product_1.id).quantity).to eq(before_quantity + 1)
    end

    it 'returns status code 200' do
      put "/cart_products/#{id}/increase"

      expect(response).to have_http_status(200)
    end
  end

  describe "PUT /cart_products/:id/decrease" do
    let (:id) { @cart_product_1.id }

    it "decreases the quantity of cart_product by 1" do
      before_quantity = @cart_product_1.quantity

      put "/cart_products/#{id}/decrease"

      expect(CartProduct.find(@cart_product_1.id).quantity).to eq(before_quantity - 1)
    end

    it 'returns status code 200' do
      put "/cart_products/#{id}/decrease"

      expect(response).to have_http_status(200)
    end
  end

end