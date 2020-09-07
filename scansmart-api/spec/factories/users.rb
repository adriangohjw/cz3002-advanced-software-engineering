require 'faker'

FactoryBot.define do
  factory :user do
    email { Faker::Internet.email }
    name { Faker::Name.name }
    password_digest { "password" }
    last_logged_in { Time.now.to_datetime }
  end
end
