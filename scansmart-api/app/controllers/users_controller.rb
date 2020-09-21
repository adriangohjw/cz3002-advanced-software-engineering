require_relative './exceptions/users_exceptions'

class UsersController < ApplicationController
  include UsersExceptionHandler

  def show
    @user = User.find(params[:id])
    json_response(@user)
  end

  def create
    raise UsersException::EmailExistError \
      if is_email_invalid(email: params[:email])

    stripe_customer = create_stripe_customer(email: params[:email])

    @user = User.create!(create_params)
    @user.stripe_customer_identifier = stripe_customer["id"]
    @user.save

    json_response(@user, :created)
  end

  def update
    @user = User.find_by(email: params[:email])

    raise UsersException::IncorrectPasswordError \
      if !@user.authenticate(params[:password_current])

    @user.password = params[:password_new]
    @user.save

    json_response(@user)
  end

  def authenticate
    user = User.find_by(email: params[:email])
    response = user.authenticate(params[:password])

    raise UsersException::IncorrectPasswordError \
      if !response
        
    json_response(response)
  end

  private

  def create_params
    params.permit(:name, :email, :password)
  end

  def is_email_invalid(email:)
    return !User.find_by(email: email).blank?
  end

  def create_stripe_customer(email:)
    verify_stripe_customer_email_validity(email: email)

    Stripe::Customer.create({
      email: email,
    })
  end

  def verify_stripe_customer_email_validity(email:)
    stripe_customers = Stripe::Customer.list(
      email: email
    )

    raise UsersException::StripeCustomerEmailExistError \
      if stripe_customers["data"].count > 0 
  end

end