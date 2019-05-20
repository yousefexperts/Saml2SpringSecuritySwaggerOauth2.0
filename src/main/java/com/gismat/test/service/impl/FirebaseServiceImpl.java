package com.gismat.test.service.impl;



import co.qyef.starter.firebase.creator.firebase.FirebaseTokenHolder;
import com.gismat.test.service.FirebaseService;
import com.gismat.test.service.shared.FirebaseParser;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {

	@Override
	public FirebaseTokenHolder parseToken(String firebaseToken) {
		return new FirebaseParser().parseToken(firebaseToken);
	}
}
