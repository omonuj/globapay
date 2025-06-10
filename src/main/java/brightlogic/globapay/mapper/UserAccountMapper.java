package brightlogic.globapay.mapper;


import brightlogic.globapay.domain.model.UserAccount;
import brightlogic.globapay.dto.request.UserAccountRequest;
import brightlogic.globapay.dto.response.UserAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {


    UserAccountResponse toResponse(UserAccount userAccount);

    UserAccount toEntity(UserAccountRequest request);

    // For updating existing entity with new values from request (optional)
    void updateEntityFromRequest(UserAccountRequest request, @MappingTarget UserAccount entity);
}
