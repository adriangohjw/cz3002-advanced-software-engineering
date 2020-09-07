require 'faker'

FactoryBot.define do
  factory :store do
    name { Faker::Address.street_name }
    address { Faker::Address.full_address }
  end
end
