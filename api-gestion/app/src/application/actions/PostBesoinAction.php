<?php

namespace gestion\application\actions;

use gestion\core\domain\entities\InputBesoinDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostBesoinAction extends AbstractAction
{
    private GestionServiceInterface $gestionService;

    public function __construct(GestionServiceInterface $gestionService)
    {
        $this->gestionService = $gestionService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $id = $rq->getAttribute('idUser');
        $body = $rq->getParsedBody();

        $inputBesoinDTO = new InputBesoinDTO($id, $body['competence_id'], $body['description']);

        $this->gestionService->creerBesoin($inputBesoinDTO);
    }
}