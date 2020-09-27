require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Products API', type: :request do
  before do
    Discount.destroy_all
    Inventory.destroy_all
    OrderProduct.destroy_all
    CartProduct.destroy_all
    Product.destroy_all    
    ProductCategory.destroy_all

    @product_category_options = ProductCategory::OPTIONS.values.sample(2).pluck(:name)
    @product_category_1 = FactoryBot.create(:product_category, name: @product_category_options.first)
    @product_category_2 = FactoryBot.create(:product_category, name: @product_category_options.second)
    
    8.times do |count|
      if count == 0
        @product_1 = FactoryBot.create(:product, product_category: @product_category_1,
                                                 price: (count + 1) * 10.0)
        FactoryBot.create(:discount_single, product: @product_1,
                                            price: (count + 1) * 10 * 0.5)
        @product_2 = FactoryBot.create(:product, product_category: @product_category_2,
                                                 price: (count + 1) * 10.0)
        FactoryBot.create(:discount_bulk, product: @product_2,
                                          price: (count + 1) * 10 * 0.5)  
      else
        FactoryBot.create(:product, product_category: @product_category_1,
                                    price: (count + 1) * 10.0)
        FactoryBot.create(:product, product_category: @product_category_2,
                                    price: (count + 1) * 10.0)
      end
    end
  end

  describe "GET /products" do
    context "no parameters passed" do
      before { get "/products" }

      it 'returns 10 results' do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 10
      end

      it 'returns results with correct values' do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].first["id"]).to eq(@product_1.id)
        expect(json_response["products"].first["name"]).to eq(@product_1.name)
        expect(json_response["products"].first["price"]).to eq(@product_1.price)
      end

      it 'returns result with discounted_price if latest discount is a single discount' do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].first["discounted_price"]).to eq(@product_1.latest_discount.price)
      end
      
      it 'do not returns result with discounted_price if latest discount is not a single discount' do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].second["discounted_price"]).to eq(nil)
      end

      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end
    end

    context "page params passed in" do
      let (:page) { 2 }
      before { get "/products?page=#{page}" }

      it "returns correct number of product results" do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 6
      end
    end

    context "category params passed in" do
      let (:category) { ProductCategory::OPTIONS.find{ |category| category[1][:name] == @product_category_1.name }[0].to_s }
      before { get "/products?category[0]=#{category}" }
      
      it "returns correct number of product results" do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 8
      end
    end

    context "min_price params passed in" do   
      let (:min_price) { 70 }
      before { get "/products?min_price=#{min_price}" }
      
      it "returns correct number of product results" do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 4
      end
    end

    context "max_price params passed in" do
      let (:max_price) { 40 }
      before { get "/products?max_price=#{max_price}" }
      
      it "returns correct number of product results" do
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 8
      end
    end

    context "both min_price and max_price params passed in" do
      let (:min_price) { 10 }
      let (:max_price) { 10 }
      before { get "/products?min_price=#{min_price}&max_price=#{max_price}" }

      it "returns product results with discounts taken into consideration" do        
        json_response = JSON.parse(response.body)
        expect(json_response["products"].count).to eq 1
      end
    end
  end
end