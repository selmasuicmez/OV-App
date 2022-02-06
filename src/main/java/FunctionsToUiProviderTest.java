//import org.junit.jupiter.api.*;
//import  org.junit.jupiter.api.Assertions;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//
//class FunctionsToUiProviderTest {
//    ProfileList profileList;
//    Profile profile;
//
//    @BeforeAll
//    public void setUpAll(){
//        System.out.println("This is beginning of the testClass");
//    }
//    @BeforeEach
//    public void setUp(){
//        profileList= new ProfileList();
//        profile =new Profile("selma","1111");
//        profileList.addProfile(profile);
//    }
//
//    //asserting if profilelist is empty after adding profile
//    @Test
//    @DisplayName("Should create profile")
//    void shouldCreateProfile() {
//        Assertions.assertFalse(profileList.getProfiles().isEmpty());
//        Assertions.assertEquals(1,profileList.getProfiles().size());
//        Assertions.assertTrue(profileList.getProfiles().stream()
//                .anyMatch(profile -> profile.getName().equals("selma") && profile.getPassword().equals("1111")));
//    }
//
////@Test
////@DisplayName("Should not create profile if username is null")
////    void shouldThrowRunTimeExceptionIfUserNameIsNull(){
////        Profile p=new Profile("","2222");
////
////        Assertions.assertThrows(RuntimeException.class,()->{
////        profileList.addProfile(p);
////        //*******  bug ********if username == null runtime exception isn't thrown
////    });
////
////}
////    @Test
////    @DisplayName("Should not create profile if password is null")
////    void shouldThrowRunTimeExceptionIfPaswordIsNull(){
////
////        Profile p=new Profile("Hichem","");
////
////        Assertions.assertThrows(RuntimeException.class,()->{
////            profileList.addProfile(p);
////        });
////        //*******  bug ********if password == null runtime exception isn't thrown
////    }
//    @AfterAll
//    public  void tearDown(){
//        System.out.println("End of the test class");
//    }
//}