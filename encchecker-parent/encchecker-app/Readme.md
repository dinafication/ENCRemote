* Pri prvom importu projekta treba pokrenuti mvn clean -P install-missing-dependencies

* Ako se u emulatoru ne prikazuje oglas treba pogledati da li se u LogCat nalazi linija slična  call adRequest.addTestDevice("483C5FDA57D1AF1EC19336E06DA421EF"); 
  te dodati device_id u listu ads:testDevices u com.google.ads.AdView
  
