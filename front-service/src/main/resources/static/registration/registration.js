angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPathAuth = 'http://localhost:5555/auth/api/v1';

    $scope.functionRegistration = function () {
        $http.post(contextPathAuth + '/registration', $scope.reguser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.marketUser = {username: $scope.reguser.username, token: response.data.token};
                $localStorage.reguser = null;
                $location.path("/");
            }
        });
    }
});