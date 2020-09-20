require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Users API', type: :request do
  before do
    @user = FactoryBot.create(:user, name: "Adrian",
                                     email: "test@example.com",
                                     password: "password")
  end

  describe "GET /users/authenticate" do
    before { get "/users/authenticate?email=#{email}&password=#{password}" }
    let(:email) { @user.email }

    context "correct password entered" do
      let(:password) { "password" }

      it 'returns the user' do
        json_response = JSON.parse(response.body)
        expect(json_response["name"]).to eq(@user.name)
        expect(json_response["email"]).to eq(@user.email)
      end

      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end
    end

    context "wrong password entered" do
      let(:password) { "wrong_password" }

      it 'returns status code 401' do
        expect(response).to have_http_status(401)
      end
    end

  end
end