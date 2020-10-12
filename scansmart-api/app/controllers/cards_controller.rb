require_relative './exceptions/users_exceptions'
require_relative './exceptions/cards_exceptions'

class CardsController < ApplicationController
  include UsersExceptionHandler
  include CardsExceptionHandler

  def index
    user = User.find(params[:id])
    stripe_customer_id = User.get_stripe_customer_by_email(email: user.email)

    stripe_customer_cards = Stripe::Customer.list_sources(
      stripe_customer_id,
      { object: 'card' },
    )

    response = Hash.new
    response[:cards] = stripe_customer_cards["data"]

    return json_response(response)
  end

  def post
    user = User.find(params[:id])
    stripe_customer_id = User.get_stripe_customer_by_email(email: user.email)

    stripe_card_token = retrieve_stripe_card_token(card_token_id: params[:card_token_id])

    begin
      stripe_customer_card = Stripe::Customer.create_source(
        stripe_customer_id,
        { source: stripe_card_token },
      )
    rescue Stripe::InvalidRequestError
      raise CardsException::StripeCardTokenExpiredError
    end

    return json_response(stripe_customer_card)
  end

  def destroy
    user = User.find(params[:id])
    stripe_customer_id = User.get_stripe_customer_by_email(email: user.email)
    
    verify_validity_of_card_id(customer_id: stripe_customer_id, card_id: params[:card_id])

    response = Stripe::Customer.delete_source(
      stripe_customer_id,
      params[:card_id],
    )

    return json_response(response)
  end
  
  private

  def verify_validity_of_card_id(customer_id:, card_id:)
    begin
      Stripe::Customer.retrieve_source(
        customer_id,
        card_id,
      )
    rescue Stripe::InvalidRequestError
      raise CardsException::StripeCustomerCardIdDoesNotExistError
    end
  end

  def retrieve_stripe_card_token(card_token_id:)
    begin
      Stripe::Token.retrieve(
        card_token_id
      )
    rescue Stripe::InvalidRequestError
      raise CardsException::StripeCustomerCardTokenIdDoesNotExistError
    end
  end

end