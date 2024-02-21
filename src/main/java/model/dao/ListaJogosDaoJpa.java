package model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import model.ListaJogos;

public class ListaJogosDaoJpa implements InterfaceDao<ListaJogos> {

    @Override
    public void incluir(ListaJogos entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void editar(ListaJogos entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void excluir(ListaJogos entidade) throws Exception {
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(ListaJogos.class, entidade.getId()));
            em.getTransaction().commit();;
        } finally {
            em.close();
        }
    }

    @Override
    public ListaJogos pesquisarPorId(int id) throws Exception {
        ListaJogos l = null;
        EntityManager em = ConnFactory.getEntityManager();
        try{
            em.getTransaction().begin();
            l = em.find(ListaJogos.class, id);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return l;
    }

    @Override
    public List<ListaJogos> listar() throws Exception {
        List<ListaJogos> lista = null;
        EntityManager em = ConnFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            lista = em.createQuery("FROM ListaJogos l").getResultList();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return lista;
    }

}
