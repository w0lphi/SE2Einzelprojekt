package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class LeaderboardControllerIntegrationTests {
    private lateinit var gameResultService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        gameResultService = spy(GameResultService())
        controller = LeaderboardController(gameResultService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        gameResultService.addGameResult(first)
        gameResultService.addGameResult(second)
        gameResultService.addGameResult(third)

        val res: List<GameResult> = controller.getLeaderboard()

        verify(gameResultService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectIdSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        gameResultService.addGameResult(first)
        gameResultService.addGameResult(second)
        gameResultService.addGameResult(third)

        val res: List<GameResult> = controller.getLeaderboard()

        verify(gameResultService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(first, res[2])
    }
}