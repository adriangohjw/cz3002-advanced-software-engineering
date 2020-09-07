FactoryBot.define do
  factory :movement do
    movement_type { Movement::MOVEMENT_TYPE_OPTIONS.values.sample }
    user_id { nil }
    store_id { nil }
  end
end
