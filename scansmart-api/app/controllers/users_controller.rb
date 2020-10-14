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

    @user = User.create_shopper_including_stripe(name: params[:name], 
                                                 email: params[:email], 
                                                 password: params[:password])

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

end