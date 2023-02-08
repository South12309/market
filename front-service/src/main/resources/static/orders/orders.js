angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    $scope.loadOrders = function () {
        $http.get(contextPathCore + '/orders')
            .then(function (response) {
                $scope.orders = response.data;
            });
    };

    $scope.loadOrders();
});