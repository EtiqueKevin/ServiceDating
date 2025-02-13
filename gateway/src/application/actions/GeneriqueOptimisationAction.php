<?php

namespace gateway\application\actions;

use gateway\application\actions\AbstractAction;

use GuzzleHttp\ClientInterface;
use GuzzleHttp\Exception\ConnectException;
use GuzzleHttp\Exception\ClientException;
use GuzzleHttp\Exception\ServerException;

use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

use Slim\Exception\HttpBadRequestException;
use Slim\Exception\HttpInternalServerErrorException;
use Slim\Exception\HttpUnauthorizedException;
use Slim\Exception\HttpForbiddenException;
use Slim\Exception\HttpNotFoundException;

class GeneriqueOptimisationAction extends AbstractAction
{

    private ClientInterface $remote_api;

    public function __construct(ClientInterface $api_client)
    {
        $this->remote_api = $api_client;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $method = $rq->getMethod();
        $path = $rq->getUri()->getPath();
        $options = ['query' => $rq->getQueryParams()];
    
        // Handle request body for POST, PUT, PATCH methods
        if (in_array($method, ['POST', 'PUT', 'PATCH'])) {
            $contentType = $rq->getHeaderLine('Content-Type');
            
            if (strpos($contentType, 'application/json') !== false) {
                $options['json'] = $rq->getParsedBody();
            } else {
                $options['form_params'] = $rq->getParsedBody();
            }
        }
    
        try {
            $rs = $this->remote_api->request($method, $path, $options);
        } catch (ConnectException | ServerException $e) {
            throw new HttpInternalServerErrorException($rq, "The remote server is not available". $e->getMessage());
        } catch (ClientException $e) {
            match($e->getCode()) {
                400 => throw new HttpBadRequestException($rq, "The request is invalid: " . $e->getMessage()),
                401 => throw new HttpUnauthorizedException($rq, "You are not authorized to access this resource"),
                403 => throw new HttpForbiddenException($rq, "You are not allowed to access this resource: " . $e->getMessage()),
                404 => throw new HttpNotFoundException($rq, "The requested resource was not found"),
                default => throw new HttpInternalServerErrorException($rq, "An unexpected error occurred")
            };
        }
        return $rs;
    }
}