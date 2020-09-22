module MovementsException

  class IncorrectMovementTypeError < StandardError
    def message
      "Your movement_type was incorrect"
    end

    def status
      :precondition_failed
    end
  end

end