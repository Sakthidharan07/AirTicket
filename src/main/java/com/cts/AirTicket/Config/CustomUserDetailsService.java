package com.cts.AirTicket.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cts.AirTicket.model.Passenger;
import com.cts.AirTicket.repository.PassengerRepository;
 
@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Passenger> user=passengerRepository.findByUsername(username);
		return user.map(CustomUserDetails::new)
			.orElseThrow(()->new UsernameNotFoundException("user not found"));
	}
	
}