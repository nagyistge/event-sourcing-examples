package net.chrisrichardson.eventstore.javaexamples.banking.web.commandside.accounts;

import net.chrisrichardson.eventstore.javaexamples.banking.backend.commandside.accounts.AccountService;
import net.chrisrichardson.eventstore.javaexamples.banking.common.accounts.CreateAccountRequest;
import net.chrisrichardson.eventstore.javaexamples.banking.common.accounts.CreateAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

  private AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CompletableFuture<CreateAccountResponse> createAccount(@Validated @RequestBody CreateAccountRequest request) {
    return accountService.openAccount(request.getCustomerId(), request.getTitle(), request.getInitialBalance(), request.getDescription())
    .thenApply(entityAndEventInfo -> new CreateAccountResponse(entityAndEventInfo.getEntityId()));
  }
}
