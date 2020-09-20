FactoryBot.define do
  factory :discount do
    product { nil }
    from_date { Time.zone.now + rand(0..10).days }
    to_date { from_date + rand(0..7).days }
  end
end
