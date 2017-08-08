package com.nlu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.dao.entity.Chat;

public interface ChatRepository  extends  JpaRepository<Chat, Integer> {

}
