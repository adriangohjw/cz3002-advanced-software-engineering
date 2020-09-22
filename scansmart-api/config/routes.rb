Rails.application.routes.draw do

  get 'users/authenticate' => 'users#authenticate'
  get 'users/' => 'users#show'
  get 'users/:id' => 'users#show'
  post 'users/' => 'users#create'
  put 'users/' => 'users#update'

  post 'movements/' => 'movements#create'

end
