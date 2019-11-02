package com.ac.reserve.web.api.service;

import com.ac.reserve.web.api.domain.User;

public interface WeChatService {

    User login(String code);

}
