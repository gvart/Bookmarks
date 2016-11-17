CREATE DATABASE mydb;

CREATE TABLE users
(
  id bigserial PRIMARY  KEY,
  username character varying(50) NOT NULL,
  password character varying(60) NOT NULL,
  email character varying(100),
  firstname character varying(50),
  lastname character varying(50),
  createdate date,
  enabled boolean DEFAULT true
);

CREATE TABLE user_roles
(
  user_role_id bigserial PRIMARY KEY,
  user_id integer NOT NULL,
  role character varying(45) NOT NULL,
  CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES public.users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uni_userid_role UNIQUE (role, user_id)
);

CREATE TABLE event_types
(
  id bigserial PRIMARY KEY ,
  name character varying(100) NOT NULL UNIQUE,
);
