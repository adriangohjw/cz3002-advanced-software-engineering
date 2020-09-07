class User < ApplicationRecord
  has_secure_password

  has_many :movements, class_name: "Movement", foreign_key: "user_id"
end
