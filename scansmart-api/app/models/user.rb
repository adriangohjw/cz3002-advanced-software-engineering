class User < ApplicationRecord
  has_secure_password

  has_many :movements, class_name: "Movement", foreign_key: "user_id"

  def self.create_user_including_stripe(name:, email:, password:)
    stripe_customer = create_stripe_customer(email: email)
    user = FactoryBot.create(:user, name: name,
                                    email: email,
                                    password: password,
                                    stripe_customer_identifier: stripe_customer.id)
    return user
  end

  def self.get_stripe_customer_by_email(email:)
    stripe_customers = Stripe::Customer.list(email: email)

    raise UsersException::StripeCustomerEmailDoesNotExistError \
      if stripe_customers["data"].count == 0 
    
    return stripe_customers["data"][0]["id"]
  end

  private

  def self.create_stripe_customer(email:)
    User.verify_stripe_customer_email_validity(email: email)

    Stripe::Customer.create({
      email: email,
    })
  end

  def self.verify_stripe_customer_email_validity(email:)
    stripe_customers = Stripe::Customer.list(
      email: email
    )

    raise UsersException::StripeCustomerEmailExistError \
      if stripe_customers["data"].count > 0 
  end
end
