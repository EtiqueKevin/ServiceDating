<?php

namespace gestion\application\actions;

use Exception;
use gestion\core\dto\AuthUserDTO;
use gestion\core\dto\InputCompetenceSalarie;
use gestion\core\dto\InputUtilisateurDTO;
use gestion\core\dto\UtilisateurDTO;
use gestion\core\services\GestionServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostUtilisateurAction extends AbstractAction
{
    private GestionServiceInterface $service;

    public function __construct( GestionServiceInterface $service)
    {
        $this->service = $service;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $params = $rq->getParsedBody() ?? null;

        if(!isset($params['id'])){


            $id = $this->service->createAuth(new AuthUserDTO($params['email'], $params['password']));
            $this->service->createUtilisateur(new InputUtilisateurDTO($id,$params['name'],$params['surname'],$params['phone']));
            $this->service->associationSalarieCompetence(new InputCompetenceSalarie($id,$params['competences']));
        }else{
            try{
                $this->service->createUtilisateur(new InputUtilisateurDTO($params['id'],$params['name'],$params['surname'],$params['phone']));
            }catch (Exception $e){
                throw new HttpBadRequestException($rq, $e->getMessage());
            }
        }



        return $rs->withStatus(201);

    }
}