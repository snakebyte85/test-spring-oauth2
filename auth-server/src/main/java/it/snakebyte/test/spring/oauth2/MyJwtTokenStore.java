package it.snakebyte.test.spring.oauth2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class MyJwtTokenStore extends JwtTokenStore {

    private ApprovalStore myApprovalStore;

    public MyJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer, ApprovalStore approvalStore) {
        super(jwtTokenEnhancer);
        setApprovalStore(approvalStore);
        this.myApprovalStore = approvalStore;
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String userId = authentication.getUserAuthentication().getName();
        String clientId = authentication.getOAuth2Request().getClientId();

        Date expireAt = ((ExpiringOAuth2RefreshToken)refreshToken).getExpiration();

        List<Approval> approvals = new ArrayList<>();

        for(String scope: authentication.getOAuth2Request().getScope()) {
            Approval approval = new Approval(userId, clientId, scope, expireAt, ApprovalStatus.APPROVED);
            approvals.add(approval);
        }

        myApprovalStore.addApprovals(approvals);
    }

}
