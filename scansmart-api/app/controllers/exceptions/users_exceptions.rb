module UsersException
  
  class EmailExistError < StandardError
    def message
      "Email already exist"
    end

    def status
      :precondition_failed
    end
  end

  class IncorrectPasswordError < StandardError
    def message
      "Your password was incorrect"
    end

    def status
      :unauthorized
    end
  end

end