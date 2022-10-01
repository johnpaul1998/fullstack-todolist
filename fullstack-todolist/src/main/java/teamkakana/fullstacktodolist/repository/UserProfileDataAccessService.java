package teamkakana.fullstacktodolist.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamkakana.fullstacktodolist.datastore.FakeUserProfileDataStore;
import teamkakana.fullstacktodolist.model.UserProfile;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserProfileDataAccessService {
    private final FakeUserProfileDataStore fakeUserProfileDataStore;
    public List<UserProfile> getUserProfiles(){
        return fakeUserProfileDataStore.getUserProfiles();
    }
}
