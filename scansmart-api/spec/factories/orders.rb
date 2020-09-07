FactoryBot.define do
  factory :order do
    user { nil }
    store { nil }
    is_verified { false }
  end
end
