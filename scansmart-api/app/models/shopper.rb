class Shopper < User
  has_many :orders, class_name: "Order", foreign_key: "user_id"
  has_many :cart_products, class_name: "CartProduct", foreign_key: "user_id"
end
