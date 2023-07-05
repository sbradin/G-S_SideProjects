import pygame
import random
import numpy as np
import sys
import pygame.font
import time
import os

# Define colors
WHITE = (238, 238, 210)
GREEN = (118, 150, 86)
BROWN = (79, 48, 31)

# Initialize Pygame and create window

# Get the base path of the executable
base_path = os.path.dirname(sys.executable)

# Set the data file paths relative to the base path
data_path = os.path.join(base_path, '../../../../data')
move_self_mp3 = os.path.join(data_path, 'move-self.mp3')
alert_mp3 = os.path.join(data_path, 'alert.mp3')
win_mp3 = os.path.join(data_path, 'win_sound.mp3')
loss_mp3 = os.path.join(data_path, 'loss_sound.mp3')
queen = os.path.join(data_path, 'LightQueen.webp')
checkedBox = os.path.join(data_path, 'checkbox_checked.png')
uncheckedBox = os.path.join(data_path, 'checkbox_unchecked.png')

def quit():
    pygame.quit()

pygame.init()
window_size = (400, 400)
window = pygame.display.set_mode(window_size)
pygame.display.set_caption("8Queens")

#move_sound = pygame.mixer.Sound("data/move-self.mp3")
move_sound = pygame.mixer.Sound(move_self_mp3)
alert_sound = pygame.mixer.Sound(alert_mp3)
win = pygame.mixer.Sound(win_mp3)
loss = pygame.mixer.Sound(loss_mp3)

# Load the chess queen image
queen_image = pygame.image.load(queen)
queen_size = min(window_size) // 9
queen_image = pygame.transform.scale(queen_image, (queen_size, queen_size))

padding = min(window_size) // 16 
board_size = min(window_size) - 2 * padding
square_size = board_size // 8
board_x = (window_size[0] - board_size) // 2
board_y = (window_size[1] - board_size) // 2

chessboard = np.empty((8, 8), dtype=str)
chessboard.fill('.')
start_move = (random.randint(0, 7), random.randint(0, 7))

players_turn = True

game_state = "menu"  # Initial game state

difficulty = None  # Variable to store the selected difficulty
difficulties = {
    "Easy": 1,
    "Medium": 2,
    "Hard": 3
}

# Define button dimensions and margin
button_width = 120
button_height = 40
button_margin = 20

checkbox_size = 20  # Size of the checkbox
checkbox_padding = 5  # Padding around the checkbox
checkbox_checked = pygame.image.load(checkedBox)  # Image for checked checkbox
checkbox_checked = pygame.transform.scale(checkbox_checked, (checkbox_size, checkbox_size))
checkbox_unchecked = pygame.image.load(uncheckedBox)  # Image for unchecked checkbox
checkbox_unchecked = pygame.transform.scale(checkbox_unchecked, (checkbox_size, checkbox_size))
show_moves = False  # Variable to track if possible moves should be shown


# Function to draw the chessboard and initial queen
def draw_chessboard_with_queen(board, next_move=None):
    window.fill(BROWN)  # Fill the window with brown color

    for row in range(8):
        for col in range(8):
            x = board_x + row * square_size
            y = board_y + col * square_size
            color = WHITE if (row + col) % 2 == 0 else GREEN
            pygame.draw.rect(window, color, pygame.Rect(x, y, square_size, square_size))

    for x in range(8):
        for y in range(8):
            if board[x][y] == "Q":
                queen_x = x * square_size + board_x
                queen_y = y * square_size + board_y
                queen_width, queen_height = queen_image.get_size()
                queen_x_offset = (square_size - queen_width) // 2
                queen_y_offset = (square_size - queen_height) // 2
                window.blit(queen_image, (queen_x + queen_x_offset, queen_y + queen_y_offset))

    if next_move is not None:
        next_queen_x = next_move[0] * square_size + board_x
        next_queen_y = next_move[1] * square_size + board_y
        board[next_move[0]][next_move[1]] = "Q"
        queen_width, queen_height = queen_image.get_size()
        queen_x_offset = (square_size - queen_width) // 2
        queen_y_offset = (square_size - queen_height) // 2
        window.blit(queen_image, (next_queen_x + queen_x_offset, next_queen_y + queen_y_offset))
    if(show_moves):
        temp_board = get_available_matrix(chessboard)
        for i in range(8):
            for j in range (8):
                if(temp_board[i][j] == "."):
                    draw_dot(i,j)


    pygame.display.flip()

def draw_dot(x, y):
    dot_radius = square_size // 8  
    dot_color = (128, 128, 128, 128) 
    dot_center = (board_x + x * square_size + square_size // 2, board_y + y * square_size + square_size // 2)
    pygame.draw.circle(window, dot_color, dot_center, dot_radius)

# Function to get the chessboard square based on the mouse position
def get_clicked_square(mouse_pos):
    row = (mouse_pos[0] - board_x) // square_size
    col = (mouse_pos[1] - board_y) // square_size

    if (
        mouse_pos[0] > 23
        and mouse_pos[0] < 372
        and mouse_pos[1] > 23
        and mouse_pos[1] < 372
        and row < 8
        and col < 8
    ):
        return row, col
    else:
        return -1, -1


def get_victory(board):
    num_queens = 0
    avail_moves = np.copy(board)
    for i in range(8):
        for j in range(8):
            if(board[i][j] == "Q"):
                temp_i = i
                temp_j = j
                while(temp_i > 0):
                    temp_i-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                while(temp_i < 7):
                    temp_i+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                while(temp_j > 0):
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_j = j
                while(temp_j < 7):
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_j = j
                while(temp_i > 0 and temp_j > 0):
                    temp_i-=1
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i < 7 and temp_j > 0):
                    temp_i+=1
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i > 0 and temp_j < 7):
                    temp_i-=1
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i < 7 and temp_j < 7):
                    temp_i+=1
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                
                num_queens +=1
                

    if(num_queens == 8):
        return 2
    else:
        posb_moves = 0
        for i in range(8):
            for j in range(8):
                if(avail_moves[i][j] == "."):
                    posb_moves += 1
        if(posb_moves == 0):
            return 1
        else:
            return 0
    

def get_available_moves(board,move = None):
    num_moves = 0
    avail_moves = get_available_matrix(board)
    for i in range(8):
        for j in range(8):
            if(avail_moves[i][j] == "."):
                num_moves +=1
    return num_moves

def get_available_matrix(board,move = None):
    avail_moves = np.copy(board)
    if(move is not None):
        avail_moves[move[0]][move[1]] = "Q"
    for i in range(8):
        for j in range(8):
            if(board[i][j] == "Q"):
                temp_i = i
                temp_j = j
                while(temp_i > 0):
                    temp_i-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                while(temp_i < 7):
                    temp_i+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                while(temp_j > 0):
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_j = j
                while(temp_j < 7):
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_j = j
                while(temp_i > 0 and temp_j > 0):
                    temp_i-=1
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i < 7 and temp_j > 0):
                    temp_i+=1
                    temp_j-=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i > 0 and temp_j < 7):
                    temp_i-=1
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j
                while(temp_i < 7 and temp_j < 7):
                    temp_i+=1
                    temp_j+=1
                    avail_moves[temp_i][temp_j] = "X"
                temp_i = i
                temp_j = j

    return avail_moves

def AI_move(board):
    #structure of dict, key = move tuple, value = #available move after said move
    possible_moves = dict()
    avail_moves = get_available_matrix(board)
    final_i = None
    final_j = None
    for i in range(8):
        for j in range(8):
            if(avail_moves[i][j] == "."):
                temp_board = np.copy(board)
                temp_board[i][j] = "Q"
                temp_avail = get_available_matrix(temp_board)
                final_i = i
                final_j = j
                for i in range(8):
                    for j in range(8):
                        if(temp_avail[i][j] == "."):
                            possible_moves[(i,j)] = get_available_moves(temp_board,(i,j))
                
    
    if(possible_moves == {}):
        possible_moves[(final_i, final_j)] = 0

    max_move = -1
    min_move = 64
    medium_moves = []
    for i,j in possible_moves:
        if(possible_moves[(i,j)] > max_move):
            max_move = possible_moves[(i,j)]
            easy_move = (i,j)
        if(possible_moves[(i,j)] < min_move):
            min_move = possible_moves[(i,j)]
            hard_move = (i,j)
        if(min_move < possible_moves[(i,j)] < max_move):
            medium_moves.append((i,j))

    if(difficulty == "Easy"):
        return easy_move
    if(difficulty == "Medium"):
        return medium_moves[random.randint(0,len(medium_moves))]
    else:
        return hard_move

running = True
invalid_move = False

def display_menu():
    window.fill(BROWN)  # Fill the window with brown color

    # Render and display the welcome message
    font = pygame.font.Font(None, 36)
    welcome_text = font.render("Welcome to 8Queens!", True, WHITE)
    welcome_rect = welcome_text.get_rect(midtop=(window_size[0] // 2, 50))
    window.blit(welcome_text, welcome_rect)

    # Calculate the total height of buttons and the gap between buttons
    total_button_height = len(difficulties) * (button_height + button_margin)
    gap = window_size[1] - (welcome_rect.bottom + total_button_height + button_height + button_margin)

    # Calculate the starting Y position for the buttons
    start_y = welcome_rect.bottom + gap // 2

    # Render and display the difficulty buttons
    for i, (difficulty_name, difficulty_value) in enumerate(difficulties.items()):
        button_color = WHITE if difficulty == difficulty_value else GREEN
        color = WHITE
        if(difficulty_value == difficulty):
            color = GREEN
        button_rect = pygame.Rect(
            window_size[0] // 2 - button_width // 2,
            start_y + i * (button_height + button_margin),
            button_width,
            button_height
        )
        pygame.draw.rect(window, button_color, button_rect)
        button_text = font.render(difficulty_name, True, color)
        button_text_rect = button_text.get_rect(center=button_rect.center)
        window.blit(button_text, button_text_rect)

    # Render and display the checkbox
    checkbox_rect = pygame.Rect(
        window_size[0] // 2 - checkbox_size + 10,
        start_y + total_button_height +5,
        checkbox_size,
        checkbox_size
    )
    checkbox_image = checkbox_checked if show_moves else checkbox_unchecked
    window.blit(checkbox_image, checkbox_rect)

    # Render and display the checkbox label
    checkbox_label = font.render("Show Possible Moves", True, WHITE)
    checkbox_label_rect = checkbox_label.get_rect(
        center =(checkbox_rect.centerx, checkbox_rect.centery - 20)
    )
    window.blit(checkbox_label, checkbox_label_rect)

    # Render and display the start button
    start_button = pygame.Rect(
        window_size[0] // 2 - button_width // 2,
        window_size[1] - button_height - button_margin,
        button_width,
        button_height
    )
    pygame.draw.rect(window, GREEN, start_button, 3)  # Draw button border
    pygame.draw.rect(window, WHITE, start_button)
    start_text = font.render("Start", True, GREEN)
    start_text_rect = start_text.get_rect(center=start_button.center)
    window.blit(start_text, start_text_rect)

    pygame.display.flip()  # Update the display



def display_result(result):
    window.fill(BROWN)  # Fill the window with brown color

    # Render and display the welcome message
    font = pygame.font.Font(None, 36)
    if(result == "victory"):
        result_text = font.render("Congratulations! You Won!", True, WHITE)
    elif(result == "defeat"):
        result_text = font.render("Too Bad! You Lost!", True, WHITE)
    elif(result == "draw"):
        result_text = font.render("Draw Game! Puzzle Complete", True, WHITE)

    result_rect = result_text.get_rect(midtop=(window_size[0] // 2, 50))
    window.blit(result_text, result_rect)
    total_button_height = 2 * (button_height + button_margin)
    gap = window_size[1] - (result_rect.bottom + total_button_height + button_height + button_margin)
    start_y = result_rect.bottom + gap // 2

    # Render and display the play again button
    play_again_button = pygame.Rect(
        window_size[0] // 2 - button_width // 2,
        window_size[1] // 2 - button_height - button_margin,
        button_width + 10,
        button_height
    )
    pygame.draw.rect(window, GREEN, play_again_button, 3)  # Draw button border
    pygame.draw.rect(window, WHITE, play_again_button)
    play_again_text = font.render("Play Again", True, GREEN)
    play_again_text_rect = play_again_text.get_rect(center=play_again_button.center)
    window.blit(play_again_text, play_again_text_rect)

    # Render and display the quit button
    quit_button = pygame.Rect(
        window_size[0] // 2 - button_width // 2,
        window_size[1] // 1.5 - button_height - button_margin,
        button_width + 10,
        button_height
    )
    pygame.draw.rect(window, GREEN, quit_button, 3)  # Draw button border
    pygame.draw.rect(window, WHITE, quit_button)
    quit_text = font.render("Quit", True, GREEN)
    quit_text_rect = quit_text.get_rect(center=quit_button.center)
    window.blit(quit_text, quit_text_rect)

    pygame.display.flip()  # Update the display




