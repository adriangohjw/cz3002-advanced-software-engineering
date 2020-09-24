require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Orders API', type: :request do
  before do
    @shopper = FactoryBot.create(:shopper)
    @store = FactoryBot.create(:store)
    @order_1 = FactoryBot.create(:order, user: @shopper, 
                                         store: @store)
    @order_2 = FactoryBot.create(:order, user: @shopper, 
                                         store: @store)
    @product_category = FactoryBot.create(:product_category)
    @product_1 = FactoryBot.create(:product, product_category: @product_category)
    @product_2 = FactoryBot.create(:product, product_category: @product_category)
    @order_product_1 = FactoryBot.create(:order_product, order: @order_1,
                                                         product: @product_1)
    @order_product_2 = FactoryBot.create(:order_product, order: @order_1,
                                                         product: @product_2)
    @order_product_3 = FactoryBot.create(:order_product, order: @order_2,
                                                         product: @product_1)
  end

  describe "GET /orders" do
    context "parameters are valid" do
      let (:order_id) { @order_1.id }
      before { get "/orders?order_id=#{order_id}" }

      it "return the order" do
        order_total_discounted_amount = 0
        @order_1.order_products.each do |order_product|
          order_total_discounted_amount += order_product.total_undiscounted_product_price - order_product.total_discount_amount
        end

        json_response = JSON.parse(response.body)
        expect(json_response["id"]).to eq(@order_1.id)
        expect(json_response["store_name"]).to eq(@store.name)
        expect(json_response["created_at"]).to eq(@order_1.created_at.to_datetime.to_s(:db))
        expect(json_response["products"].count).to eq(@order_1.order_products.count)
        expect(json_response["total_discounted_amount"]).to eq(order_total_discounted_amount)
        expect(json_response["products"][0]["id"]).to eq(@order_1.order_products[0].product.id)
        expect(json_response["products"][0]["name"]).to eq(@order_1.order_products[0].product.name)
        expect(json_response["products"][0]["total_undiscounted_product_price"]).to eq(@order_1.order_products[0].total_undiscounted_product_price)
        expect(json_response["products"][0]["quantity"]).to eq(@order_1.order_products[0].quantity)
        expect(json_response["products"][0]["total_discount_amount"]).to eq(@order_1.order_products[0].total_discount_amount)
        expect(json_response["products"][0]["total_discounted_price"]).to eq(
          @order_1.order_products[0].total_undiscounted_product_price - @order_1.order_products[0].total_discount_amount
        )
      end

      it "returns status code 200" do
        expect(response).to have_http_status(200)
      end
    end

    context "order does not exist" do
      let (:order_id) { 0 }
      before { get "/orders?order_id=#{order_id}" }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end
  end

  describe "GET /users/:user_id/orders" do
    context "parameters are valid" do
      let(:user_id) { @shopper.id }
      before { get "/users/#{user_id}/orders" }

      it "return the orders" do
        json_response = JSON.parse(response.body)
        expect(json_response["orders"].count).to eq(@shopper.orders.count)
        expect(json_response["orders"][0]["id"]).to eq(@order_1.id)
        expect(json_response["orders"][0]["details"]["store_name"]).to eq(@store.name)
        expect(json_response["orders"][0]["details"]["items_purchased_count"]).to eq(@shopper.orders[0].order_products.count)
        expect(json_response["orders"][0]["details"]["created_at"]).to eq(@store.orders[0].created_at.to_datetime.to_s(:db))
        expect(json_response["orders"][1]["id"]).to eq(@order_2.id)
        expect(json_response["orders"][1]["details"]["store_name"]).to eq(@store.name)
        expect(json_response["orders"][1]["details"]["items_purchased_count"]).to eq(@shopper.orders[1].order_products.count)
        expect(json_response["orders"][1]["details"]["created_at"]).to eq(@store.orders[1].created_at.to_datetime.to_s(:db))
      end

      it "returns status code 200" do
        expect(response).to have_http_status(200)
      end
    end

    context "user does not exist" do
      before { get "/users/#{user_id}/orders" }
      let(:user_id) { nil }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end
  end
end