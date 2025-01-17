FactoryBot.define do
  factory :discount_bulk do
    product { nil }
    from_date { Time.zone.now + rand(0..10).days }
    to_date { from_date + rand(0..7).days }
    bulk_quantity { rand(1..3) }
  end
end
