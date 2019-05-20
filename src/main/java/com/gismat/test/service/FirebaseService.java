package com.gismat.test.service;


import co.qyef.starter.firebase.creator.firebase.FirebaseTokenHolder;

public interface FirebaseService {

	FirebaseTokenHolder parseToken(String idToken);

}
