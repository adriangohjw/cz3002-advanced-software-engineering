require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Movements API', type: :request do
  before do
    @user = FactoryBot.create(:user)
    @store = FactoryBot.create(:store)
  end

  describe "POST /movements" do
    context "parameters are valid" do
      let(:movement_type) { Movement::MOVEMENT_TYPE_OPTIONS.values.sample }
      let(:user_id) { @user.id }
      let(:store_id) { @store.id }

      it "create the Movement record" do
        expect(Movement.count).to eq(0)
        
        post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}"
        
        expect(Movement.count).to eq(1)
      end

      it "return the movement" do
        post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}"

        json_response = JSON.parse(response.body)
        expect(json_response["store_id"]).to eq(@store.id)
        expect(json_response["user_id"]).to eq(@user.id)
      end

      it "returns status code 201" do
        post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}"

        expect(response).to have_http_status(201)
      end
    end

    context "movement_type is not part of the options" do
      before { post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}" }
      let(:movement_type) { "wrong_option" }
      let(:user_id) { @user.id }
      let(:store_id) { @store.id }

      it 'returns status code 412' do
        expect(response).to have_http_status(412)
      end
    end

    context "user does not exist" do
      before { post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}" }
      let(:movement_type) { Movement::MOVEMENT_TYPE_OPTIONS.values.sample }
      let(:user_id) { nil }
      let(:store_id) { @store.id }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end

    context "store does not exist" do
      before { post "/movements?movement_type=#{movement_type}&user_id=#{user_id}&store_id=#{store_id}" }
      let(:movement_type) { Movement::MOVEMENT_TYPE_OPTIONS.values.sample }
      let(:user_id) { @user.id }
      let(:store_id) { nil }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end
  end
end