package please.review.core.exception

class InvalidGithubRepoFullNameException(
    fullNameSeparator: String
) : RuntimeException("잘못된 저장소 형식입니다. 유효 형식 : 소유자${fullNameSeparator}저장소명")