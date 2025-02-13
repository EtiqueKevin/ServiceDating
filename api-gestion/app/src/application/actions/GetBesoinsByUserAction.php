<?php

namespace gestion\application\actions;

use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class GetBesoinsByUserAction extends AbstractAction
{
    private GestionServiceInterface $gestion_service;

    public function __construct(GestionServiceInterface $gestion_service)
    {
        $this->gestion_service = $gestion_service;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $limit = $rq->getQueryParams()['limit'] ?? null;
        $offset = $rq->getQueryParams()['offset'] ?? null;
        $id = $rq->getAttribute('idUser');
        $besoins = $this->gestion_service->getBesoinsByUser($id, $limit, $offset);

        $res = [
            'type' => 'ressources',
            'besoins' => $besoins
        ];

        $rs->getBody()->write(json_encode($res));
        return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);
    }
}