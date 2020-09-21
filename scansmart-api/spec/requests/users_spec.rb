require 'rails_helper'
require 'factory_bot_rails'
require 'json'

RSpec.describe 'Users API', type: :request do
  before do
    @user = FactoryBot.create(:user, name: "Adrian",
                                     email: "test@example.com",
                                     password: "password")
  end

  describe "GET /users/:id" do
    before { get "/users/#{id}" }

    context "user exist" do
      let(:id) { @user.id }

      it 'returns the user' do
        json_response = JSON.parse(response.body)
        expect(json_response["name"]).to eq(@user.name)
        expect(json_response["email"]).to eq(@user.email)
      end

      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end
    end

    context "user does not exist" do
      let(:id) { 100 }

      it 'returns status code 404' do
        expect(response).to have_http_status(404)
      end
    end
  end

  describe "POST /users" do
    before { post "/users?name=#{name}&email=#{email}&password=#{password}" }
    let(:name) { "Adrian" }
    let(:password) { "password" }

    context "user does not exist" do
      let(:email) { "new_user@example.com" }

      it 'returns the user' do
        json_response = JSON.parse(response.body)
        expect(json_response["name"]).to eq("Adrian")
        expect(json_response["email"]).to eq("new_user@example.com")
      end

      it 'returns status code 200' do
        expect(response).to have_http_status(201)
      end
    end

    context "user does exist" do
      let(:email) { @user.email }

      it 'returns status code 412' do
        expect(response).to have_http_status(412)
      end
    end
  end

  describe "PUT /users" do
    before { put "/users?email=#{email}&password_current=#{password_current}&password_new=#{password_new}" }
    let(:email) { @user.email }
    let(:password_new) { "new_password" }

    context "user current password is wrong" do
      let(:password_current) { "wrong_password" }

      it 'returns status code 401' do
        expect(response).to have_http_status(401)
      end
    end

    context "user current password is correct" do
      let(:password_current) { "password" }

      it 'user password is updated' do
        expect(User.find(@user.id).authenticate("new_password")).to be_truthy
      end

      it 'returns status code 200' do
        expect(response).to have_http_status(200)
      end
    end
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