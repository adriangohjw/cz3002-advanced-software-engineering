module ApplicationHelper
  # convert to YYYY-MM-DD HH:mm:ss e.g. 2020-08-21 13:12:45
  def sanitize_timezone_object(datetime)
    datetime.to_datetime.to_s(:db)
  end
end