import pygame
import random
import numpy as np
import pygame.font
import time
import sys
import os
import logic as eq



font = pygame.font.Font(None, 36)
welcome_text = font.render("Welcome to 8Queens!", True, eq.WHITE)
welcome_rect = welcome_text.get_rect(midtop=(eq.window_size[0] // 2, 50))
total_button_height = len(eq.difficulties) * (eq.button_height + eq.button_margin)
gap = eq.window_size[1] - (welcome_rect.bottom + total_button_height + eq.button_height + eq.button_margin)
start_y = welcome_rect.bottom + gap // 2


while eq.running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            eq.running = False
        elif eq.game_state == "menu":
            eq.display_menu()
            if event.type == pygame.MOUSEBUTTONUP:
                mouse_pos = pygame.mouse.get_pos()

                # Check if a difficulty button was clicked
                for i, (difficulty_name, difficulty_value) in enumerate(eq.difficulties.items()):
                    button_rect = pygame.Rect(
                        eq.window_size[0] // 2 - eq.button_width // 2,
                        start_y + i * (eq.button_height + eq.button_margin),
                        eq.button_width,
                        eq.button_height
                    )
                    if button_rect.collidepoint(mouse_pos):
                        eq.difficulty = difficulty_value

                # Check if the "Show Possible Moves" checkbox was clicked
                checkbox_rect = pygame.Rect(
                    eq.window_size[0] // 2 - eq.checkbox_size + 10,
                    start_y + total_button_height + 5,
                    eq.checkbox_size,
                    eq.checkbox_size
                )
                if checkbox_rect.collidepoint(mouse_pos):
                    eq.show_moves = not eq.show_moves

                # Check if the "Start" button was clicked
                start_button_rect = pygame.Rect(
                    eq.window_size[0] // 2 - eq.button_width // 2,
                    eq.window_size[1] - eq.button_height - eq.button_margin,
                    eq.button_width,
                    eq.button_height
                )
                if start_button_rect.collidepoint(mouse_pos):
                    eq.game_state = "playing"
                    start = True

                if eq.game_state == "menu":
                    eq.display_menu()

        elif eq.game_state == "playing":
            if(start):
                # Initial draw of the chessboard with queen
                eq.draw_chessboard_with_queen(eq.chessboard, eq.start_move)
                pygame.display.flip()
                start = False
            if eq.get_victory(eq.chessboard) > 0:
                if eq.get_victory(eq.chessboard) == 1:
                    time.sleep(2)
                    if eq.players_turn:
                        eq.game_state = "done"
                        end_state = "defeat"  # Player lost
                        eq.loss.play()
                    else:
                        eq.game_state = "done"
                        end_state = "victory"  # Player won
                        eq.win.play()
                elif eq.get_victory(eq.chessboard) == 2:
                    time.sleep(2)
                    eq.game_state = "done"
                    end_state = "draw"  # All 8 queens placed, draw
            elif not eq.players_turn:
                next_move = eq.AI_move(eq.chessboard)
                eq.draw_chessboard_with_queen(eq.chessboard, next_move)
                eq.move_sound.play()
                eq.players_turn = True
            elif event.type == pygame.MOUSEBUTTONUP:
                mouse_pos = pygame.mouse.get_pos()
                clicked_square = eq.get_clicked_square(mouse_pos)
                if (
                    clicked_square[0] != -1
                    and eq.get_available_matrix(eq.chessboard)[clicked_square[0]][clicked_square[1]] == "."
                    and eq.players_turn
                ):
                    eq.draw_chessboard_with_queen(eq.chessboard, clicked_square)
                    eq.move_sound.play()
                    eq.invalid_move = False
                    eq.players_turn = False
                else:
                    if eq.chessboard[clicked_square[0]][clicked_square[1]] == "Q" or not eq.players_turn:
                        eq.invalid_move = False
                    if not eq.invalid_move:
                        eq.alert_sound.play()  # Play alert sound
                        eq.invalid_move = True
        elif(eq.game_state == "done"):
            if end_state == "victory":
                eq.display_result("victory")
            elif end_state == "defeat":
                eq.display_result("defeat")
            elif end_state == "draw":
                eq.display_result("draw")

            if event.type == pygame.MOUSEBUTTONUP:
                mouse_pos = pygame.mouse.get_pos()

                # Check if the "Start" button was clicked
                play_again_button_rect = pygame.Rect(
                    eq.window_size[0] // 2 - eq.button_width // 2,
                    eq.window_size[1] // 2 - eq.button_height - eq.button_margin,
                    eq.button_width,
                    eq.button_height
                )
                if play_again_button_rect.collidepoint(mouse_pos):
                    eq.game_state = "menu"
                    eq.chessboard = np.empty((8, 8), dtype=str)
                    eq.chessboard.fill('.')
                    eq.start_move = (random.randint(0, 7), random.randint(0, 7))
                    eq.players_turn = True
                
                quit_button_rect = pygame.Rect(
                    eq.window_size[0] // 2 - eq.button_width // 2,
                    eq.window_size[1] // 1.5 - eq.button_height - eq.button_margin,
                    eq.button_width,
                    eq.button_height
                )
                if quit_button_rect.collidepoint(mouse_pos):
                    eq.quit()

                if eq.game_state == "menu":
                    eq.display_menu()
                

    if(eq.game_state == "playing"):
        eq.draw_chessboard_with_queen(eq.chessboard)


    pygame.display.flip()



# Quit Pygame
eq.quit()