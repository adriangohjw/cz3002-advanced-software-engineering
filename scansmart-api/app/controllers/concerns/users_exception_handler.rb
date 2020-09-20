require_relative '../exceptions/users_exceptions'

module UsersExceptionHandler
  extend ActiveSupport::Concern

  included do
    rescue_from UsersException::EmailExistError do |e|
      json_response({ message: e.message }, e.status)
    end

    rescue_from UsersException::IncorrectPasswordError do |e|
      json_response({ message: e.message }, e.status)
    end
  end
end