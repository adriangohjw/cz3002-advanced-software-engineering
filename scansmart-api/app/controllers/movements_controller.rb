require_relative './exceptions/movements_exceptions'

class MovementsController < ApplicationController
  include MovementsExceptionHandler

  def create
    raise MovementsException::IncorrectMovementTypeError \
      if !is_movement_type_valid(movement_type: params[:movement_type])

    user = User.find(params[:user_id])
    store = Store.find(params[:store_id])
    @movement = Movement.create(movement_type: params[:movement_type],
                                user: user,
                                store: store)

    json_response(@movement, :created)
  end

  private

  def is_movement_type_valid(movement_type:)
    return Movement::MOVEMENT_TYPE_OPTIONS.values.include?(movement_type)
  end
end