package org.example.currencyexchange.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.api.model.ExchangeRequest;
import org.example.currencyexchange.domain.CurrencyExchangeService;
import org.example.currencyexchange.util.ExchangeApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;

    @Operation(summary = "Make an exchange transaction between user's currency's accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Exchange performed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Depends on the issue, you can get several exception messages regarding not meeting the Request body requirements",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "User's account with provided UUID does not exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "There is no enough money to make this exchange",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class))
            ),
            @ApiResponse(responseCode = "502",
                    description = "Error while processing the data from API",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExchangeApiResponse.class))
            )
    })
    @PatchMapping("/{userAccountId}")
    public ResponseEntity<ExchangeApiResponse> makeExchange(@RequestBody @Valid
                                                            @io.swagger.v3.oas.annotations.parameters.RequestBody
                                                                    (description = "Request body containing 2 fields od 3 capital " +
                                                                            "digits currency's code (USD, PLN) and amount of the exchange",
                                                                            required = true,
                                                                            content = @Content(
                                                                                    schema = @Schema(implementation = ExchangeRequest.class)))
                                                            ExchangeRequest exchangeRequest,
                                                            @Parameter(description = "User's account ID (UUID)")
                                                            @Schema(example = "3b704d48-f2a1-11ee-8ce6-d09466eff245")
                                                            @PathVariable String userAccountId) {
        currencyExchangeService.exchangeCurrencies(exchangeRequest, userAccountId);
        return ok(ExchangeApiResponse.of("Exchange performed successfully"));
    }
}
