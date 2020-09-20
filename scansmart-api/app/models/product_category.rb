class ProductCategory < ApplicationRecord
  has_many :products, class_name: "Product", foreign_key: "product_category_id"

  OPTIONS = {
    :fruits_vegetables => {
      :name => "Fruits & Vegetables",
      :check_required => false
    },
    :meat => {
      :name => "Meat & Seafood",
      :check_required => false
    },
    :cooking_essentials => {
      :name => "Rice & Cooking Essentials",
      :check_required => false
    },
    :beverages => {
      :name => "Beverages",
      :check_required => false
    },
    :household => {
      :name => "Household",
      :check_required => false
    },
    :mother_baby => {
      :name => "Mother & Baby",
      :check_required => false
    },
    :dairies => {
      :name => "Dairy, Chilled & Eggs",
      :check_required => false
    },
    :snacks => {
      :name => "Choco, Snacks, Sweets",
      :check_required => false
    },
    :bakery => {
      :name => "Bakery & Breakfast",
      :check_required => false
    },
    :alcohol => {
      :name => "Wines, Beers & Spirits",
      :check_required => true
    },
    :health => {
      :name => "Health",
      :check_required => true
    },
    :pets => {
      :name => "Pet Care",
      :check_required => false
    },
    :beauty => {
      :name => "Beauty",
      :check_required => false
    },
    :kitchen => {
      :name => "Kitchen & Dining",
      :check_required => false
    }
  }

end
