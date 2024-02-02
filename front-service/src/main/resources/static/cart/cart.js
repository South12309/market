angular.module('market').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    $scope.loadCart = function () {
        $http.get(contextPathCarts + '/cart/' + $localStorage.marketGuestCartId)
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.clearCart = function (id) {
        $http.get(contextPathCarts + '/cart/' + $localStorage.marketGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    }
    $scope.deleteFromCart = function (id) {
        $http.get(contextPathCarts + '/cart/' + $localStorage.marketGuestCartId + '/remove/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.createOrder = function (address, phone) {
        $http.post(contextPathCore + '/orders/' + address + '/' + phone)
            .then(function (response) {
                alert("Заказ оформлен")
                $scope.loadCart();
            });

    }


    $scope.increase = function (id) {
        $http.get(contextPathCarts + '/cart/' + $localStorage.marketGuestCartId +'/inc/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.decrease = function (id) {
        $http.get(contextPathCarts + '/cart/' + $localStorage.marketGuestCartId +'/dec/' + id)
            .then(function (response) {
                $scope.loadCart();
            });
    }



    $scope.loadCart();
});