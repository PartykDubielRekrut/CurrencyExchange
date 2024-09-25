package org.example.currencyexchange.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.api.model.UserAccountRequest;
import org.example.currencyexchange.api.model.UserAccountResponse;
import org.example.currencyexchange.domain.UserAccountService;
import org.example.currencyexchange.util.ExchangeApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.example.currencyexchange.util.ExchangeApiResponse.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @Operation(summary = "Create new user's account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Depends on the issue, you can get several exception messages regarding not meeting the Request body requirements",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<ExchangeApiResponse> createNewUserAccount(@RequestBody @Valid UserAccountRequest userAccountRequest) {
        String userAccountUuid = userAccountService.createUser(userAccountRequest);
        return new ResponseEntity<>(of("User was created. Their account ID: " + userAccountUuid), CREATED);
    }

    @Operation(summary = "Get the user's account details of specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAccountResponse.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "User's account with provided UUID does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserAccountResponse> showUserAccountInfo(@Parameter(description = "User's account ID (UUID)")
                                                                   @Schema(example = "3b704d48-f2a1-11ee-8ce6-d09466eff245")
                                                                   @PathVariable String id) {
        UserAccountResponse userAccountDto = userAccountService.showUserAccountInfo(id);
        return ok(userAccountDto);
    }
}
