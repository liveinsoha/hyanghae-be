package flaskspring.demo.tag.controller;

import flaskspring.demo.config.auth.MemberDetails;
import flaskspring.demo.exception.BaseResponse;
import flaskspring.demo.exception.BaseResponseCode;
import flaskspring.demo.tag.dto.req.ReqTagIndexes;
import flaskspring.demo.tag.dto.res.ResRegisteredTag;
import flaskspring.demo.tag.service.TagService;
import flaskspring.demo.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Tag(name = "태그 설정 기능", description = "태그 설정 API")

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;


    @Operation(summary = "태그 저장", description = "유저의 태그를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageUtils.SUCCESS),
            @ApiResponse(responseCode = "401", description = MessageUtils.UNAUTHORIZED),
    })
    @PostMapping("")
    public ResponseEntity<BaseResponse<Object>> saveTags(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody ReqTagIndexes request) {
        Long myMemberId = memberDetails.getMemberId();
        tagService.saveMemberTags(myMemberId, request.getTagIndexes());
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseCode.OK, new HashMap<>()));
    }


    @Operation(summary = "등록된 태그 조회", description = "유저가 등록한 태그를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록된 태그 조회 성공"),
            @ApiResponse(responseCode = "401", description = MessageUtils.UNAUTHORIZED),
    })
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<ResRegisteredTag>>> registeredTagGet(@AuthenticationPrincipal MemberDetails memberDetails) {
        Long myMemberId = memberDetails.getMemberId();
        List<ResRegisteredTag> registeredTags = tagService.getRegisteredTag(myMemberId);

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseCode.OK, registeredTags));
    }

}