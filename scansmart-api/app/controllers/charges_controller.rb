class ChargesController < ApplicationController
  def show
    begin
      charge = Stripe::Charge.retrieve(
        params[:charge_id],
      )

      json_response(charge)

    rescue Stripe::InvalidRequestError => exception
      response = Hash.new
      response[:error] = exception.message

      json_response(response, :not_found)
    end
  end

# charge default card if no "card_id" param passed in
  def post
    begin
      charge = Stripe::Charge.create({
        amount: params[:amount],
        currency: 'sgd',
        customer: params[:customer_id],
        source: params[:card_id]
      })

      json_response(charge, :created)

    rescue Stripe::InvalidRequestError => exception
      response = Hash.new
      response[:error] = exception.message

      json_response(response, :bad_request)
    end
  end
end